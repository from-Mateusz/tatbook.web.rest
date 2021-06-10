package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class TattooistProfileLegitimation {

    private LocalDateTime confirmationDate;

    private LocalDateTime rejectionDate;

    public void confirm() {
        if(!isConfirmed() && isRejected()) {
            this.confirmationDate = LocalDateTime.now();
        }
    }

    public boolean isConfirmed() {
        return null != confirmationDate;
    }

    public void reject() {
        if(!isConfirmed() && isRejected()) {
            this.rejectionDate = rejectionDate;
        }
    }

    public boolean isRejected() {
        return null != rejectionDate;
    }
}
