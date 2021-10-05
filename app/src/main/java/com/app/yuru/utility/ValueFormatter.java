package com.app.yuru.utility;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class ValueFormatter extends com.github.mikephil.charting.formatter.ValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        switch ((int) entry.getX()) {
            case 0:
                return "Openness";
            case 1:
                return "Conscientiousness";
            case 2:
                return "Neuroticism";
            case 3:
                return "Agreeableness";
            case 4:
                return "Extraversion";
            default:
                return "default";
        }
    }
}
