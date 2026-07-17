package com.coaching.util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Helper to persist uploaded files (student/faculty photos, question images)
 * to disk under the webapp's /uploads directory. Uses the built-in
 * Servlet 5 (Jakarta) multipart support - no commons-fileupload jar required.
 */
public class FileUploadUtil {

    private FileUploadUtil() {
    }

    /**
     * Saves the given Part to {@code baseUploadDir/subFolder/} with a random
     * file name (extension preserved) and returns the relative web path
     * (e.g. "uploads/students/ab12cd.jpg") to store in the DB, or null if
     * the part has no file content.
     */
    public static String saveFile(Part part, String baseUploadDir, String subFolder) throws IOException {
        if (part == null || part.getSize() <= 0) {
            return null;
        }
        String originalName = extractFileName(part);
        String ext = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0) {
            ext = originalName.substring(dot);
        }
        String newName = UUID.randomUUID().toString().replace("-", "") + ext;

        File dir = new File(baseUploadDir, subFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Path target = new File(dir, newName).toPath();
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return "uploads/" + subFolder + "/" + newName;
    }

    private static String extractFileName(Part part) {
        String header = part.getHeader("content-disposition");
        if (header == null) {
            return "file";
        }
        for (String token : header.split(";")) {
            token = token.trim();
            if (token.startsWith("filename")) {
                String name = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                int idx = name.lastIndexOf('/');
                int idx2 = name.lastIndexOf('\\');
                if (idx2 > idx) idx = idx2;
                return idx >= 0 ? name.substring(idx + 1) : name;
            }
        }
        return "file";
    }
}
