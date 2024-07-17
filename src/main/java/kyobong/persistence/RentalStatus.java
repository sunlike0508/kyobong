package kyobong.persistence;

import kyobong.config.EnumType;

public enum RentalStatus implements EnumType {

    AVAILABLE("대여가능"), OUT_OF_STOCK("대여중"), DAMAGED("책 손상"), LOST_BOOK("분실"), SYSTEM_ERROR("기타 대여 불가");

    private final String description;


    RentalStatus(String description) {
        this.description = description;
    }


    public static int getCategoryCount() {
        return RentalStatus.values().length;
    }


    @Override
    public String getName() {
        return this.name();
    }


    @Override
    public String getDescription() {
        return this.description;
    }
}
