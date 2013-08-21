package com.df.blobstore.bundle;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public interface BundleKey extends Writable, RawComparator<String>, WritableComparable<String> {

}
