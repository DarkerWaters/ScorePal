package uk.co.darkerwaters.scorepal.application;

/*
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.util.Pair;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
*/
/**
 * A utility for performing read/write operations on Drive files via the REST API and opening a
 * file picker UI via Storage Access Framework.
 *
 * https://ammar.lanui.online/integrate-google-drive-rest-api-on-android-app-bc4ddbd90820
 */
public class DriveServiceHelper {
    /*
    private final Executor mExecutor;
    private final Drive mDriveService;

    private static DriveServiceHelper Instance = null;

    public DriveServiceHelper(Drive driveService) {
        // remember the service
        mDriveService = driveService;
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public static DriveServiceHelper InitialiseService(Drive googleDriveService) {
        if (null == Instance)
            Instance = new DriveServiceHelper(googleDriveService);
        return Instance;
    }

    public static DriveServiceHelper GetInstance() {
        return Instance;
    }

    // Creates a text file in the user's My Drive folder and returns its file ID.
    public Task<String> createFile() {
        return Tasks.call(mExecutor, new Callable<String>() {
            @Override
            public String call() throws Exception {
                File metadata = new File()
                        .setParents(Collections.singletonList("root"))
                        .setMimeType("text/plain")
                        .setName("Untitled file");
                File googleFile = mDriveService.files().create(metadata).execute();
                if (googleFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }
                return googleFile.getId();
            }
        });
    }

    // Opens the file identified by {@code fileId} and returns a {@link Pair} of its name and
    //contents.
    public Task<Pair<String, String>> readFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<Pair<String, String>>() {
            @Override
            public Pair<String, String> call() throws Exception {
                // Retrieve the metadata as a File object.
                File metadata = mDriveService.files().get(fileId).execute();
                String name = metadata.getName();

                // Stream the file contents to a String.
                try (InputStream is = mDriveService.files().get(fileId).executeMediaAsInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String contents = stringBuilder.toString();

                    return Pair.create(name, contents);
                }
            }
        });
    }

    // Updates the file identified by {@code fileId} with the given {@code name} and {@code
    // content}.
    public Task<Void> saveFile(final String fileId, final String name, final String content) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Create a File containing any metadata changes.
                File metadata = new File().setName(name);

                // Convert content to an AbstractInputStreamContent instance.
                ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);

                // Update the metadata and contents.
                mDriveService.files().update(fileId, metadata, contentStream).execute();
                return null;
            }
        });
    }

    public Task<FileList> queryFiles() {
        return Tasks.call(mExecutor, new Callable<FileList>() {
            @Override
            public FileList call() throws Exception {
                return mDriveService.files().list().setSpaces("drive").execute();
            }
        });
    }

    // Returns an {@link Intent} for opening the Storage Access Framework file picker.
    public Intent createFilePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        return intent;
    }

    public Task<Pair<String, String>> openFileUsingStorageAccessFramework(final ContentResolver contentResolver, final Uri uri) {
        return Tasks.call(mExecutor, new Callable<Pair<String, String>>() {
            @Override
            public Pair<String, String> call() throws Exception {
                // Retrieve the document's display name from its metadata.
                String name;
                try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        name = cursor.getString(nameIndex);
                    } else {
                        throw new IOException("Empty cursor returned for file.");
                    }
                }

                // Read the document's contents as a String.
                String content;
                try (InputStream is = contentResolver.openInputStream(uri);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    content = stringBuilder.toString();
                }

                return Pair.create(name, content);
            }
        });
    }
    */
}
