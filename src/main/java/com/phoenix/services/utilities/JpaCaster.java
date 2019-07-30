package com.phoenix.services.utilities;

import java.util.ArrayList;
import java.util.List;

public class JpaCaster {

    public static <T> List<T> castObjectListToType(List list) throws IllegalArgumentException {

        if (list == null) throw new IllegalArgumentException("Parameter 'list' is null.");

        if (list.size() != 0){

            //Result list
            List<t> res;
            if (clazz.isInstance(list.get(0).getClass())) {
                for (Object obj : list) {

                }
            }

        }else {
            return new ArrayList<T>();
        }



        return List<T>;
    }
}
