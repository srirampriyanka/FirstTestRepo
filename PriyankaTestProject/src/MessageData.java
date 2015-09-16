// ****************************************************************
// Copyright (c) 2015 Cisco Systems, Inc.
// All rights reserved.
// Unauthorized redistribution prohibited.
// ****************************************************************


/**
 * Used to store data to populate NGFW exceptions.
 */
public final class MessageData {

    public static final String DEFAULT_PROPERTIES = "messages.ExceptionMessages";
    /**
     * The non-interpolated error message for this exception. This message will
     * be interpolated by the validation engine.
     */
    private final String messageTemplate;

    /**
     * The instance associated with the violation.
     */
    private Object offendingBean;

    /**
     * The list of variable elements in the error message. If the template
     * includes variables, this provides a translation of all the variables in
     * the template. The template may include a variable portion represented as
     * {n} where n is the variable position in the expressionVariables. The list
     * is position-specific with respect to the English representation of the
     * message. If the English representation of the message reads
     * "The {0} doors {1} sedan", the first parameter will be the number of
     * doors and the second would be the color, in a different language this
     * could be translated as "La macchina {1} con {0} porte" and the order of
     * the parameters could be rendered in a different order.
     *
     * See {@link MessageDataFormatter} and {@link java.text.MessageFormat} for
     * further details.
     */
    private String[] expressionVariables;

    /**
     * This variable will indicate which field of the class caused a validation
     * error.
     *
     * Examples:
     * * If TCPPortObject has an invalid name, then location = "name"
     * * If PhysicalInterface's IP address has invalid dhcpRoutemetric, then
     * location = "ipv4.dhcpRouteMetric"
     * * If an IPAddress in invalid in an array of PhysicalInterface's
     * ipAddresses, then location = "ipv6.ipAddresses[0].ipAddress"
     */
    private String location;

    public MessageData(String messageTemplate, Object offendingBean) {
        this(messageTemplate, "", offendingBean, (String[]) null);
    }

    public MessageData(String messageTemplate, String... expressionVariables) {
        this(messageTemplate, null, "", expressionVariables);
    }

    public MessageData(String messageTemplate, Object offendingBean, String... expressionVariables) {
        this(messageTemplate, "", offendingBean, expressionVariables);
    }

    public MessageData(String messageTemplate, String location, Object offendingBean) {
        this(messageTemplate, location, offendingBean, (String[]) null);
    }

    @SafeVarargs
    public MessageData(String messageTemplate, String location, Object offendingBean, String... expressionVariables) {
        this.messageTemplate = messageTemplate;
        this.location = location;
        this.offendingBean = offendingBean;
        this.expressionVariables = expressionVariables;
    }

    public String getMessageTemplate() {
        return this.messageTemplate;
    }

    public Object getOffendingBean() {
        return this.offendingBean;
    }

    public String[] getExpressionVariables() {
        return expressionVariables;
    }

    public void setExpressionVariables(String[] expressionVariables) {
        this.expressionVariables = expressionVariables;
    }

   }
