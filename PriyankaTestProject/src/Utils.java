/*
 * Utils.java
 * Copyright (c) 2015 by cisco Systems, Inc.
 * All rights reserved.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Collection of utils required by models
 * 
 * Created by dsounthi on 4/17/15.
 */
public class Utils {

    private static final String REST_RESOURCE_PACKAGE = "com.cisco.ngfw.onbox.rest.resources";
    private static final String PRODUCTION = "production";

    // all static methods
    private Utils() {

    }

    public static Class getResourceClassForEntity(String name) {
        Class resourceClass = null;
        try {
            String className = String.format("%s.%sResource", REST_RESOURCE_PACKAGE, name);
            resourceClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
        }
        return resourceClass;
    }

    /**
     * Utility method to encode parameters that are used to construct a
     * MessageData
     * into a single string. This may be used to serialize a MessageData - only
     * the
     * data part - into a format that can be persisted into database. Note the
     * offendingBean parameter is not supported here.
     * 
     * @param messageTemplate
     *            The messageTemplate parameter to the MessageData constructor.
     * @param expressionVariables
     *            Variables in the messageTemplate to construct a full message.
     * @return A CSV string containing all the input values.
     */
    public static String encodeMsgData(String messageTemplate, String... expressionVariables) {
        StringBuilder sb;
        try {
            sb = new StringBuilder(URLEncoder.encode(messageTemplate, "UTF-8"));
            if (expressionVariables != null) {
                for (String var : expressionVariables) {
                    if (null != var) {
                        sb.append(",").append(URLEncoder.encode(var, "UTF-8"));
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        return sb.toString();
    }

    /**
     * Decode a CSV string and construct a MessageData instance using the parsed
     * field values from the input.
     * 
     * @param encodedStr
     *            A CSV string that was produced by the encodeMsgData() method.
     * @return A MessageData instance with the parsed field values from the
     *         input
     *         string.
     */
    public static MessageData decodeMsgData(String encodedStr) {
        String[] toks = encodedStr.split(",");
        String messageTemplate = toks[0];
        String[] vars = new String[toks.length - 1];
        for (int i = 0; i < vars.length; i++) {
            try {
                vars[i] = URLDecoder.decode(toks[i + 1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
        return new MessageData(messageTemplate, null, null, vars);
    }

    /**
     * Utility to relocate file path based on root of the file system. This
     * method will relocate only if active profile is production.
     * 
     * @param filePath path to be relocated
     * @return relocated path
     */
    public static String relocateFile(String filePath) {
        // TODO: right now we are just hardcoding root as /ngfw. Eventually we
        // need to load ims.conf and get root of the file system or
        // use relocate method from com.cisco.ngfw.utils.Util of CD.
        if (null == filePath || (null != filePath && filePath.trim().length() == 0)) {
            return filePath;
        }
        String relocatedPath = filePath;
        final String root = "/ngfw";
        if (PRODUCTION.equalsIgnoreCase((String) System.getProperties().get(CommonConstants.APPLICATION_PROFILE))
                && !filePath.startsWith(root)) {
            relocatedPath = String.format("%s%s", root, filePath);
        }
        return relocatedPath;
    }
}
