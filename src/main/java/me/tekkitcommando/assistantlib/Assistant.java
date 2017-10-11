package me.tekkitcommando.assistantlib;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.tekkitcommando.assistantlib.command.Command;
import me.tekkitcommando.assistantlib.command.CommandHandler;
import me.tekkitcommando.assistantlib.event.CommandEvent;
import me.tekkitcommando.assistantlib.service.ServiceHandler;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Assistant {

    @Getter
    private final String name;
    @Getter
    private final CommandHandler commandHandler;
    @Getter
    private final ServiceHandler serviceHandler;
    @Getter
    private SpeechClient client;

    private TargetDataLine targetDataLine;
    private AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, true);
    private File command;

    public Assistant(String name, CommandHandler commandHandler, ServiceHandler serviceHandler) {
        this.name = name;
        this.commandHandler = commandHandler;
        this.serviceHandler = serviceHandler;
        try {
            client = SpeechClient.create();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerCommand(String commandLabel, Command command) {
        commandHandler.addCommand(commandLabel, command);
    }

    public void startListening() {
        if (command != null)
            command = null;

        commandToFile();
    }

    public void stopListening() {
        targetDataLine.stop();
        targetDataLine.close();
        handleCommandFromFile(command);
    }

    private void commandToFile() {
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String fileName = dateFormat.format(date);

            command = new File(fileName + ".wav");
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCommandFromFile(File file) {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] data = new byte[0];

        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteString audioBytes = ByteString.copyFrom(data);

        // Builds the sync recognize request
        RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setSampleRateHertz(44100)
                .setLanguageCode("en-US")
                .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = client.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();

        for (SpeechRecognitionResult result : results) {
            List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();

            for (SpeechRecognitionAlternative alternative : alternatives) {
                new CommandEvent(this, alternative.getTranscript());
            }
        }
    }
}
