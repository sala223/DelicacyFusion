package com.df.core.common.utils;

public interface DataMarshaller{

    public byte[] seal(byte[] data);

    public byte[] disclose(byte[] securedData);
}
