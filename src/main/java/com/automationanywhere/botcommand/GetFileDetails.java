package com.automationanywhere.botcommand;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import static com.automationanywhere.commandsdk.model.AttributeType.FILE;
import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.NUMBER;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

//BotCommand makes a class eligible for being considered as an action.
@BotCommand

//CommandPks adds required information to be dispalable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "GetFileDetails", label = "[[GetFileDetails.label]]",
        node_label = "[[GetFileDetails.node_label]]", description = "[[GetFileDetails.description]]", icon = "pkg.svg",

        //Return type information. return_type ensures only the right kind of variable is provided on the UI.
        return_label = "[[GetFileDetails.return_label]]", return_type = NUMBER, return_required = true, return_description = "[[GetFileDetails.return_label_description]]")
public class GetFileDetails {

    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public NumberValue action(
            //Idx 1 would be displayed first, with a text box for entering the value.
            @Idx(index = "1", type = FILE)
            //UI labels.
            @Pkg(label = "[[GetFileDetails.filePath.label]]")
            //Ensure that a validation error is thrown when the value is null.
            @NotEmpty
                    String filePath) {

        //Internal validation, to disallow empty strings. No null check needed as we have NotEmpty on firstString.
        if ("".equals(filePath.trim()))
            throw new BotCommandException("Please make sure to select a valid file");


        //Business logic
        Path fullpathofFile;
        Double result = 0.0;
        try{
            fullpathofFile = Paths.get(filePath);
            BasicFileAttributes attributes = Files.readAttributes(fullpathofFile, BasicFileAttributes.class);
            result = Double.valueOf(attributes.size());
        } catch (Exception e){
            throw new BotCommandException("There was an issue getting the size of file " + filePath +". Full Exception Text: " + e.toString());
        }

        //Return NumberValue
        return new NumberValue(result);
    }
}
