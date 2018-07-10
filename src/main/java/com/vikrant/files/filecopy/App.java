package com.vikrant.files.filecopy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

/**
 * @author Vikrant Wagh
 *
 */
public class App {
	public void copy(String sourceDirectory, String destinationDirectory) {
		try {
			File source = new File(sourceDirectory);
			File destination = new File(destinationDirectory);
			Path destinationPath = Paths.get(destinationDirectory);

			System.out.println(
					"Total size of Source Folder : " + FileUtils.sizeOfDirectory(source) / (1024.0 * 1024.0) + " MBs");

			List<File> sourceFiles = Files.walk(Paths.get(sourceDirectory))
					.map(p -> new File(p.toAbsolutePath().toString())).collect(Collectors.toList());
			List<File> destinationFiles = Files
					.walk(Files.exists(destinationPath) ? destinationPath : Files.createDirectory(destinationPath))
					.map(p -> new File(p.toAbsolutePath().toString())).collect(Collectors.toList());

			if (sourceFiles != null && !sourceFiles.isEmpty()) {
				for (File file : sourceFiles) {
					if (file.isFile()) {
						List<File> list = destinationFiles.stream()
								.filter(p -> p.getName().equalsIgnoreCase(file.getName())).collect(Collectors.toList());
						if (list != null && !list.isEmpty()) {
							File dFile = list.get(0);
							if (dFile != null) {

								if (FileUtils.checksumCRC32(file) != FileUtils.checksumCRC32(dFile)) {
									System.out.println("Copying changed file : " + file.getAbsolutePath());
									copyFiles(source, destination, file);
								}
							}
						} else {
							copyFiles(source, destination, file);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void copyFiles(File sourcePath, File destinationPath, File sourceFile) throws IOException {
		Path sPath = sourcePath.toPath();
		Path dPath = destinationPath.toPath();
		Path sfPath = sourceFile.toPath();

		Path relativeSourcePath = sPath.relativize(sfPath.getParent());
		Path relativeDestinationPath = Paths.get(dPath.toString() + "/" + relativeSourcePath);

		if (!Files.exists(relativeDestinationPath)) {
			Files.createDirectories(relativeDestinationPath);
		}

		Files.copy(sfPath, Paths.get(relativeDestinationPath.toAbsolutePath() + "/" + sfPath.getFileName()),
				StandardCopyOption.REPLACE_EXISTING);
	}
}
