package com.df.blobstore.bundle;

import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Writable;

public interface BundleValue extends Writable, RawComparator<BinaryComparable> {

    boolean isMarkedAsDeleted();

    byte[] getRawData();

}
