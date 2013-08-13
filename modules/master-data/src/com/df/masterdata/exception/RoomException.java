package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class RoomException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "Room";

    public static final int ROOM_WITH_NAME_EXIST = 100000;

    public static final int ROOM_WITH_ID_NOT_EXIST = 100001;

    public static final int EXCEED_TABLE_CAPACITY = 100002;

    public RoomException(Throwable cause, int errorCode) {
	super(cause, REALM, errorCode);
    }

    public RoomException(Throwable cause, int errorCode, String messageFormat, Object... args) {
	super(cause, REALM, errorCode, messageFormat, args);
    }

    public RoomException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }

    public static RoomException roomWithNameAlreadyExist(String roomName) {
	return new RoomException(ROOM_WITH_NAME_EXIST, "Room name=%s already exist.", roomName);
    }

    public static RoomException roomWithIdNotExist(long roomId) {
	return new RoomException(ROOM_WITH_ID_NOT_EXIST, "Room id=%s does not exist.", roomId);
    }

    public static RoomException exceedTableCapacity(long roomId) {
	return new RoomException(EXCEED_TABLE_CAPACITY, "Exceed the table capacity in room %s", roomId);
    }
}
