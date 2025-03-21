import com.google.api.client.http.FileContent;
//import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Drive;
import com.google.api.services.drive.model.DriveList;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DriveSnippets {
  private com.google.api.services.drive.Drive service;

  public DriveSnippets(com.google.api.services.drive.Drive service) {
    this.service = service;
  }

  public String createDrive() throws IOException {
    com.google.api.services.drive.Drive driveService = this.service;
    // [START createDrive]
    Drive driveMetadata = new Drive();
    driveMetadata.setName("Project Resources");
    String requestId = UUID.randomUUID().toString();
    Drive drive = driveService.drives().create(requestId,
        driveMetadata)
        .execute();
    System.out.println("Drive ID: " + drive.getId());
    // [END createDrive]
    return drive.getId();
  }

  public List<Drive> recoverDrives(String realUser)
      throws IOException {
    com.google.api.services.drive.Drive driveService = this.service;
    List<Drive> drives = new ArrayList<Drive>();
    // [START recoverDrives]
    // Find all shared drives without an organizer and add one.
    // Note: This example does not capture all cases. Shared drives
    // that have an empty group as the sole organizer, or an
    // organizer outside the organization are not captured. A
    // more exhaustive approach would evaluate each shared drive
    // and the associated permissions and groups to ensure an active
    // organizer is assigned.
    String pageToken = null;
    Permission newOrganizerPermission = new Permission()
        .setType("user")
        .setRole("organizer")
        .setEmailAddress("user@example.com");
    // [START_EXCLUDE silent]
    newOrganizerPermission.setEmailAddress(realUser);
    // [END_EXCLUDE]

    do {
      DriveList result = driveService.drives().list()
          .setQ("organizerCount = 0")
          .setFields("nextPageToken, drives(id, name)")
          .setUseDomainAdminAccess(true)
          .setPageToken(pageToken)
          .execute();
      for (Drive drive : result.getDrives()) {
        System.out.printf("Found drive without organizer: %s (%s)\n",
            drive.getName(), drive.getId());
        // Note: For improved efficiency, consider batching
        // permission insert requests
        Permission permissionResult = driveService.permissions()
            .create(drive.getId(), newOrganizerPermission)
            .setUseDomainAdminAccess(true)
            .setSupportsAllDrives(true)
            .setFields("id")
            .execute();
        System.out.printf("Added organizer permission: %s\n",
            permissionResult.getId());

      }
      // [START_EXCLUDE silent]
      drives.addAll(result.getDrives());
      // [END_EXCLUDE]
      pageToken = result.getNextPageToken();
    } while (pageToken != null);
    // [END recoverDrives]
    return drives;
  }
}
