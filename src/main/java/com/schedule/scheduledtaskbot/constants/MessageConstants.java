package com.schedule.scheduledtaskbot.constants;

public interface MessageConstants {

    String HELP = """
            /help - commands information
            
            /register - to work with bot you should be registered, just send command /register
            
            /get_table - to return google sheet with code usage history
            
            /take - to take file write command in such way: /take filename_server, example: /take backend/api/ehd/storage_manager.py_test
            
            /return - to return file write command in such way: /return filename_server, example: /return backend/api/ehd/storage_manager.py_test
            
            /last_busy_files - to get last 5 busy files, can be used with number parameter: /last_busy_files 10 - will return last 10 busy files
            
            /my_busy_files - to get my busy files
            
            /return_all - to return all files from chosen server""";

    String COMMAND_FORMAT = "Command should start with \"/\"";

    String WHITE_SPACE = " ";

    String EMPTY_STRING = "";

    String SLASH = "/";

    String SEMICOLON = ";";

    String YOU_HAVE_ALREADY_REGISTERED = "You have already registered as %s";

    String YOU_HAVE_SUCCESSFULLY_REGISTERED = "You have successfully registered as %s";
}

