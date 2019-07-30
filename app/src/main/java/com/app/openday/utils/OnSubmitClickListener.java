package com.app.openday.utils;

import com.app.openday.network.response.Record;

import java.util.List;

public interface OnSubmitClickListener {
    void onSubmitAttendance(List<Record> studentListWithAttendance);
}
