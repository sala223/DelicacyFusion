package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class RoomException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "Room";

    public static final int ROOM_WITH_NAME_EXIST = 100000;

    public static final int ROOM_WITH_CODE_NOT_EXIST = 100001;

    public static final int EXCEED_TABLE_CAPACITY = 100002;

    public static final int ROOM_IS_NOT_EMPTY = 100003;
    
    public static final int ROOM_WITH_CODE_EXIST = 100004;

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

    public static RoomException roomWithCodeAlreadyExist(String roomCode) {
	return new RoomException(ROOM_WITH_CODE_EXIST, "Room code=%s already exist.", roomCode);
    }

    public static RoomException roomWithCodeNotExist(String roomCode) {
	return new RoomException(ROOM_WITH_CODE_NOT_EXIST, "Room code=%s does not exist.", roomCode);
    }

    public static RoomException exceedTableCapacity(String roomCode) {
	return new RoomException(EXCEED_TABLE_CAPACITY, "Exceed the table capacity in room %s", roomCode);
    }

    public static RoomException roomIsNotEmpty(String roomCode) {
	return new RoomException(ROOM_IS_NOT_EMPTY, "Cannot remove room %s as it is not empty.", roomCode);
    }
}
