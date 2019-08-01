package com.phoenix.services.utilities;

import java.util.ArrayList;
import java.util.List;

public class JpaCaster {

    public static <T> List<T> castObjectsListToType(Class<T> type, List list) throws IllegalArgumentException, ClassCastException {

        //Check whether parameters are null
        if (type == null || list == null) throw new IllegalArgumentException(" One of parameters is null");

        //Create result array
        List<T> result = new ArrayList<>();

        //Check whether list size is zero
        if (list.size() == 0) return result;
        else {
            //Cast to "type" parameter
            for (Object obj : list) {
                T res = type.cast(obj);
                result.add(res);
            }
            return result;
        }

    }
}
