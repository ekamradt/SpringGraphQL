package com.example.springmadness.helper;


import com.example.springmadness.model.Name;

public class NameHelper {

    public static boolean mergeName(Name existingName, Name updatedName) {
        final MergeHelper helper = new MergeHelper();
        existingName.setFirstName(helper.update(existingName.getFirstName(), updatedName.getFirstName()))
                .setLastName(helper.update(existingName.getLastName(), updatedName.getLastName()));
        return helper.isChanged();
    }
}
