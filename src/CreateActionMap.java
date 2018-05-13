import java.util.HashMap;

public class CreateActionMap {
    //todo switch to configurable file
    private static final String DOWNLOAD_ADASCORP_NAME = "downloadAdascorp";
    private static final String DOWNLOAD_ADASCORP_COMMAND = "wget";
    private static final String DOWNLOAD_ARATECH_NAME = "downloadAratech";
    private static final String DOWNLOAD_ARATECH_COMMAND = "scp";
    private static final String DOWNLOAD_CEC_NAME = "downloadCEC";
    private static final String DOWNLOAD_CEC_COMMAND = "mv-sec";
    private static final String DOWNLOAD_CZERKA_NAME = "downloadCzerka";
    private static final String DOWNLOAD_CZERKA_COMMAND = "CPREM";
    private static final String DOWNLOAD_GSI_NAME = "downloadGSI";
    private static final String DOWNLOAD_GSI_COMMAND = "get-rm";

    private static final String DEVICE_INFO_ADASCORP_NAME = "deviceInfoAdascorp";
    private static final String DEVICE_INFO_ADASCORP_COMMAND = "ls-l";
    private static final String DEVICE_INFO_ARATECH_NAME = "deviceInfoAratech";
    private static final String DEVICE_INFO_ARATECH_COMMAND = "alist";
    private static final String DEVICE_INFO_CEC_NAME = "deviceInfoCEC";
    private static final String DEVICE_INFO_CEC_COMMAND = "dir";
    private static final String DEVICE_INFO_CZERKA_NAME = "deviceInfoCzerka";
    private static final String DEVICE_INFO_CZERKA_COMMAND = "ll";
    private static final String DEVICE_INFO_GSI_NAME = "deviceInfoGSI";
    private static final String DEVICE_INFO_GSI_COMMAND = "dir-a";

    private static final String DEVICE_USE_ADASCORP_NAME = "deviceUseAdascorp";
    private static final String DEVICE_USE_ADASCORP_COMMAND = "open";
    private static final String DEVICE_USE_ARATECH_NAME = "deviceUseAratech";
    private static final String DEVICE_USE_ARATECH_COMMAND = "clear";
    private static final String DEVICE_USE_CEC_NAME = "deviceUseCEC";
    private static final String DEVICE_USE_CEC_COMMAND = "od";
    private static final String DEVICE_USE_CZERKA_NAME = "deviceUseCzerka";
    private static final String DEVICE_USE_CZERKA_COMMAND = "s-entry";
    private static final String DEVICE_USE_GSI_NAME = "deviceUseGSI";
    private static final String DEVICE_USE_GSI_COMMAND = "cmd";

    private static final String SCAN_ADASCORP_NAME = "scanAdascorp";
    private static final String SCAN_ADASCORP_COMMAND = "nmap";
    private static final String SCAN_ARATECH_NAME = "scanAratech";
    private static final String SCAN_ARATECH_COMMAND = "map";
    private static final String SCAN_CEC_NAME = "scanCEC";
    private static final String SCAN_CEC_COMMAND = "sec-scan";
    private static final String SCAN_CZERKA_NAME = "scanCzerka";
    private static final String SCAN_CZERKA_COMMAND = "scan";
    private static final String SCAN_GSI_NAME = "scanGSI";
    private static final String SCAN_GSI_COMMAND = "mapAll";

    private static final String CAPTURE_ADASCORP_NAME = "captureAdascorp";
    private static final String CAPTURE_ADASCORP_COMMAND = "msf";
    private static final String CAPTURE_ARATECH_NAME = "captureAratech";
    private static final String CAPTURE_ARATECH_COMMAND = "exploitator";
    private static final String CAPTURE_CEC_NAME = "captureCEC";
    private static final String CAPTURE_CEC_COMMAND = "pwn-or";
    private static final String CAPTURE_CZERKA_NAME = "captureCzerka";
    private static final String CAPTURE_CZERKA_COMMAND = "powaEx";
    private static final String CAPTURE_GSI_NAME = "captureGSI";
    private static final String CAPTURE_GSI_COMMAND = "hydra";

    private static final String SHOWMAP_NAME = "showMap";
    private static final String SHOWMAP_COMMAND = "show-map";
    private static final String PUNISH_NAME = "punish";

    private HashMap<String, Action> actionsNamesMap;
    private HashMap<String, String> actionsCommandsMap;
    //todo create own class takes 2 string and one Action

    public CreateActionMap() {
        HashMap<String, Action> actionsNames = new HashMap<>();
        HashMap<String, String> actionsCommands = new HashMap<>();
        /** downloads **/
        actionsCommands.put(DOWNLOAD_ADASCORP_COMMAND, DOWNLOAD_ADASCORP_NAME);
        actionsNames.put(DOWNLOAD_ADASCORP_NAME, new Action_Download());
        actionsCommands.put(DOWNLOAD_ARATECH_COMMAND, DOWNLOAD_ARATECH_NAME);
        actionsNames.put(DOWNLOAD_ARATECH_NAME, new Action_Download());
        actionsCommands.put(DOWNLOAD_CEC_COMMAND, DOWNLOAD_CEC_NAME);
        actionsNames.put(DOWNLOAD_CEC_NAME, new Action_Download());
        actionsCommands.put(DOWNLOAD_CZERKA_COMMAND, DOWNLOAD_CZERKA_NAME);
        actionsNames.put(DOWNLOAD_CZERKA_NAME, new Action_Download());
        actionsCommands.put(DOWNLOAD_GSI_COMMAND, DOWNLOAD_GSI_NAME);
        actionsNames.put(DOWNLOAD_GSI_NAME, new Action_Download());
        /** device info **/
        actionsCommands.put(DEVICE_INFO_ADASCORP_COMMAND, DEVICE_INFO_ADASCORP_NAME);
        actionsNames.put(DEVICE_INFO_ADASCORP_NAME, new Action_Device_Info());
        actionsCommands.put(DEVICE_INFO_ARATECH_COMMAND, DEVICE_INFO_ARATECH_NAME);
        actionsNames.put(DEVICE_INFO_ARATECH_NAME, new Action_Device_Info());
        actionsCommands.put(DEVICE_INFO_CEC_COMMAND, DEVICE_INFO_CEC_NAME);
        actionsNames.put(DEVICE_INFO_CEC_NAME, new Action_Device_Info());
        actionsCommands.put(DEVICE_INFO_CZERKA_COMMAND, DEVICE_INFO_CZERKA_NAME);
        actionsNames.put(DEVICE_INFO_CZERKA_NAME, new Action_Device_Info());
        actionsCommands.put(DEVICE_INFO_GSI_COMMAND, DEVICE_INFO_GSI_NAME);
        actionsNames.put(DEVICE_INFO_GSI_NAME, new Action_Device_Info());
        /** device use **/
        actionsCommands.put(DEVICE_USE_ADASCORP_COMMAND, DEVICE_USE_ADASCORP_NAME);
        actionsNames.put(DEVICE_USE_ADASCORP_NAME, new Action_Device_Use());
        actionsCommands.put(DEVICE_USE_ARATECH_COMMAND, DEVICE_USE_ARATECH_NAME);
        actionsNames.put(DEVICE_USE_ARATECH_NAME, new Action_Device_Use());
        actionsCommands.put(DEVICE_USE_CEC_COMMAND, DEVICE_USE_CEC_NAME);
        actionsNames.put(DEVICE_USE_CEC_NAME, new Action_Device_Use());
        actionsCommands.put(DEVICE_USE_CZERKA_COMMAND, DEVICE_USE_CZERKA_NAME);
        actionsNames.put(DEVICE_USE_CZERKA_NAME, new Action_Device_Use());
        actionsCommands.put(DEVICE_USE_GSI_COMMAND, DEVICE_USE_GSI_NAME);
        actionsNames.put(DEVICE_USE_GSI_NAME, new Action_Device_Use());
        /** scan **/
        actionsCommands.put(SCAN_ADASCORP_COMMAND, SCAN_ADASCORP_NAME);
        actionsNames.put(SCAN_ADASCORP_NAME, new Action_Scan());
        actionsCommands.put(SCAN_ARATECH_COMMAND, SCAN_ARATECH_NAME);
        actionsNames.put(SCAN_ARATECH_NAME, new Action_Scan());
        actionsCommands.put(SCAN_CEC_COMMAND, SCAN_CEC_NAME);
        actionsNames.put(SCAN_CEC_NAME, new Action_Scan());
        actionsCommands.put(SCAN_CZERKA_COMMAND, SCAN_CZERKA_NAME);
        actionsNames.put(SCAN_CZERKA_NAME, new Action_Scan());
        actionsCommands.put(SCAN_GSI_COMMAND, SCAN_GSI_NAME);
        actionsNames.put(SCAN_GSI_NAME, new Action_Scan());
        /** capture **/
        actionsCommands.put(CAPTURE_ADASCORP_COMMAND, CAPTURE_ADASCORP_NAME);
        actionsNames.put(CAPTURE_ADASCORP_NAME, new Action_Capture());
        actionsCommands.put(CAPTURE_ARATECH_COMMAND, CAPTURE_ARATECH_NAME);
        actionsNames.put(CAPTURE_ARATECH_NAME, new Action_Capture());
        actionsCommands.put(CAPTURE_CEC_COMMAND, CAPTURE_CEC_NAME);
        actionsNames.put(CAPTURE_CEC_NAME, new Action_Capture());
        actionsCommands.put(CAPTURE_CZERKA_COMMAND, CAPTURE_CZERKA_NAME);
        actionsNames.put(CAPTURE_CZERKA_NAME, new Action_Capture());
        actionsCommands.put(CAPTURE_GSI_COMMAND, CAPTURE_GSI_NAME);
        actionsNames.put(CAPTURE_GSI_NAME, new Action_Capture());
        /** always available **/
        actionsCommands.put(SHOWMAP_COMMAND, SHOWMAP_NAME);
        actionsNames.put(SHOWMAP_NAME, new Action_ShowMap());
        actionsNamesMap = actionsNames;
        actionsCommandsMap = actionsCommands;
    }

    public HashMap<String, Action> getActionsNames() {
        return actionsNamesMap;
    }

    public HashMap<String, String> getActionsCommands() {
        return actionsCommandsMap;
    }
}