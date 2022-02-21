package com.asterion.webtesting.utilities;

import java.util.List;

public class Statistics {

    public static float average(List<Float> floats){
        float sum = 0;
        for (Float f : floats) {
            sum  += f;
        } return sum / floats.size();
    }
}
