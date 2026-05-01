package com.acme.cargotrak.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Hand-rolled state machine for shipment status transitions.
 *
 * DRAFT -> BOOKED -> IN_TRANSIT -> DELIVERED -> INVOICED -> CLOSED
 * (DRAFT can also be cancelled)
 *
 * @author Rajesh Kumar 2008-12
 * @author L. Chen 2012-09 added INVOICED step
 */
public class ShipmentStateMachine {

    private static final Map<String, Set<String>> ALLOWED = new HashMap<String, Set<String>>();

    static {
        Set<String> fromDraft = new HashSet<String>();
        fromDraft.add("BOOKED");
        fromDraft.add("CANCELLED");
        ALLOWED.put("DRAFT", fromDraft);

        Set<String> fromBooked = new HashSet<String>();
        fromBooked.add("IN_TRANSIT");
        fromBooked.add("CANCELLED");
        ALLOWED.put("BOOKED", fromBooked);

        Set<String> fromTransit = new HashSet<String>();
        fromTransit.add("DELIVERED");
        ALLOWED.put("IN_TRANSIT", fromTransit);

        Set<String> fromDelivered = new HashSet<String>();
        fromDelivered.add("INVOICED");
        ALLOWED.put("DELIVERED", fromDelivered);

        Set<String> fromInvoiced = new HashSet<String>();
        fromInvoiced.add("CLOSED");
        ALLOWED.put("INVOICED", fromInvoiced);

        ALLOWED.put("CLOSED",    new HashSet<String>());
        ALLOWED.put("CANCELLED", new HashSet<String>());
    }

    public boolean canTransition(String from, String to) {
        if (from == null || to == null) return false;
        Set<String> allowed = ALLOWED.get(from);
        return allowed != null && allowed.contains(to);
    }

    public Set<String> nextStates(String from) {
        Set<String> s = ALLOWED.get(from);
        if (s == null) return new HashSet<String>();
        return s;
    }
}
