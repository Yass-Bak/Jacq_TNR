package com.example.tnr.Utils;

import org.monte.screenrecorder.ScreenRecorder;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.Scenario;
import static org.monte.media.VideoFormatKeys.*;

public class VideoRecorderUtils {
    private static CustomScreenRecorder recorder;
    private static final Logger logger = LoggerFactory.getLogger(VideoRecorderUtils.class);

    // Custom ScreenRecorder extension to get access to the created file
    private static class CustomScreenRecorder extends ScreenRecorder {
        private File createdMovieFile;

        public CustomScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea,
                                    Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat,
                                    File movieFolder) throws Exception {
            super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        }

        @Override
        protected File createMovieFile(Format fileFormat) throws IOException {
            createdMovieFile = super.createMovieFile(fileFormat);
            return createdMovieFile;
        }

        public File getCreatedMovieFile() {
            return createdMovieFile;
        }
    }

    public static void initializeScreenRecording() {
        File videoDir = new File("target/videos");
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        try {
            // Fixed format configuration with properly specified keys
            Format screenFormat = new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI);

            Format videoFormat = new Format(
                    MediaTypeKey, MediaType.VIDEO,
                    EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    DepthKey, 24,
                    FrameRateKey, Rational.valueOf(15),
                    QualityKey, 1.0f,
                    KeyFrameIntervalKey, 15 * 60
            );

            Format mouseFormat = new Format(
                    MediaTypeKey, MediaType.VIDEO,
                    EncodingKey, "black",
                    FrameRateKey, Rational.valueOf(30)
            );

            recorder = new CustomScreenRecorder(
                    gc,
                    new Rectangle(0, 0, 1920, 1080),
                    screenFormat,
                    videoFormat,
                    mouseFormat,
                    null,
                    videoDir
            );

            recorder.start();
            logger.info("Screen recording started ");
        } catch (Exception e) {
            logger.error("Error starting screen recording", e);
        }
    }

    public static void turnOffRecord(Scenario scenario) {
        if (recorder != null) {
            try {
                // First, stop the recorder properly to ensure the file is complete
                recorder.stop();

                // Wait a moment for file operations to complete
                Thread.sleep(1000);

                File recordedFile = recorder.getCreatedMovieFile();

                if (recordedFile != null && recordedFile.exists()) {
                    if (scenario.isFailed()) {
                        String sanitizedScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "-");
                        String newVideoPath = "target/videos/" + sanitizedScenarioName + "-" + System.currentTimeMillis() + ".avi";
                        File newFile = new File(newVideoPath);

                        // Ensure Allure results directory exists
                        File allureResultsDir = new File("target/allure-results");
                        if (!allureResultsDir.exists()) {
                            allureResultsDir.mkdirs();
                        }

                        // Create an Allure-friendly video path
                        String allureVideoName = sanitizedScenarioName + "-video.avi";
                        File allureVideoFile = new File(allureResultsDir, allureVideoName);

                        // If there's an existing Allure video file, delete it
                        if (allureVideoFile.exists()) {
                            allureVideoFile.delete();
                        }

                        // Copy the video to both the videos directory and Allure results
                        Files.copy(recordedFile.toPath(), newFile.toPath());
                        Files.copy(recordedFile.toPath(), allureVideoFile.toPath());

                        // Delete the original file
                        recordedFile.delete();

                        // Attach video to scenario if possible using the Cucumber API
                        // This may work depending on your Cucumber version
                        try {
                            // Attempt to attach the video file to the scenario
                            // Note: This might not work with all Cucumber versions for video content
                            byte[] videoBytes = Files.readAllBytes(allureVideoFile.toPath());
                            scenario.attach(videoBytes, "video/x-msvideo", "Video Recording");
                            logger.info("Video attached to scenario in Allure report");
                        } catch (Exception e) {
                            logger.warn("Could not attach video to scenario: " + e.getMessage());
                        }

                        // Create an Allure attachment file to link the video
                        try {
                            String attachmentContent = "<!DOCTYPE html><html><body>" +
                                    "<video width='100%' height='100%' controls>" +
                                    "<source src='" + allureVideoName + "' type='video/x-msvideo'>" +
                                    "Your browser does not support the video tag." +
                                    "</video></body></html>";

                            File htmlAttachment = new File(allureResultsDir, sanitizedScenarioName + "-video.html");
                            Files.write(htmlAttachment.toPath(), attachmentContent.getBytes());
                            logger.info("Created HTML video player for Allure report");
                        } catch (Exception e) {
                            logger.warn("Could not create HTML attachment: " + e.getMessage());
                        }

                        logger.info("Saved videos: " + newVideoPath + " and for Allure: " + allureVideoFile.getPath());
                    } else {
                        // Delete the recorded file if the test passed
                        if (recordedFile.delete()) {
                            logger.info("Deleted recorded video for passed test");
                        } else {
                            logger.warn("Could not delete recorded video at " + recordedFile.getAbsolutePath());
                        }
                    }
                } else {
                    logger.error("No recorded video file found or file does not exist");
                }
            } catch (Exception e) {
                logger.error("Screen recording error", e);
                e.printStackTrace();
            } finally {
                recorder = null;
            }
        }
    }
}