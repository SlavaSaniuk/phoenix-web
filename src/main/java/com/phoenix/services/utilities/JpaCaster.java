package com.phoenix.services.utilities;

import java.util.ArrayList;
import java.util.List;

public class JpaCaster {

    /**
     * Method cast untyped {@link List} to type specified in {@link Class} "type" parameter. If given list has different elements types method skip this.
     * @param type - {@link Class} to cast.
     * @param list - given untyped {@link List}.
     * @param <T> - any type for cast.
     * @return - Typed list of 'T' class.
     * @throws NullPointerException - If one of method parameters in {@code null}.
     */
    public static <T> List<T> castObjectsListToType(Class<T> type, List list) throws NullPointerException {

        //Check whether parameters are null
        if (type == null || list == null) throw new NullPointerException(" One of parameters is null");

        //Create result array
        List<T> result = new ArrayList<>();

        //Check whether list size is zero
        if (list.size() == 0) return result;
        else {
            //Cast to "type" parameter
            for (Object obj : list) {
                T elem;
                try{
                    elem = type.cast(obj);
                }catch (ClassCastException exc) {
                    continue;
                }
                result.add(elem);
            }
            return result;
        }

    }
}
