import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReassignDate
{
	public static void main(String[] args) {
		List<String> fileNames = new ArrayList<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(System.getProperty("user.dir")))) {
			for (Path path : directoryStream) {
				fileNames.add(path.toString());
			}
		} catch (IOException ex) {}

		Collections.sort(fileNames);
		Collections.reverse(fileNames);
		Date currentDate = new Date();

		for (String fileName : fileNames) {
			BasicFileAttributeView attributes = Files.getFileAttributeView(Paths.get(fileName), BasicFileAttributeView.class);
			FileTime time = FileTime.fromMillis(currentDate.getTime());
			try {
				attributes.setTimes(time, time, time);
			} catch (IOException e) {
				e.printStackTrace();
			}
			currentDate.setTime(currentDate.getTime() - TimeUnit.DAYS.toMillis(1));
		}
	}
}
