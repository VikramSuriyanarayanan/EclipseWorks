package ReduceJoin;
//package databaseJoins;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class TuplePair  implements WritableComparable<TuplePair> {
	
	private int tag;
	private String tupleValue;
	
	public void set(String tuple,int t){
		tupleValue=tuple;
		tag=t;
	}
	
	/*public TuplePair(String tupleValue, int tag) {
		super();
		this.tupleValue = tupleValue;
		this.tag = tag;
	}*/
//	public TuplePair(){
//		//this(null,0);
//	}
	
	public String getTupleValue() {
		return tupleValue;
	}	
	
	

	@Override
	public String toString() {
		return "TuplePair [tag=" + tag + ", tupleValue=" + tupleValue + "]";
	}

	public int getTag() {
		return tag;
	} 
	
	@Override
	public void readFields(DataInput inp) throws IOException {
		
		tupleValue= WritableUtils.readString(inp);
		tag=inp.readInt();
	}
	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, tupleValue);
//		out.writeBytes(tupleValue+"\n");
		out.writeInt(tag);
	}
/*	
	public static class Comparator extends WritableComparator{

		public Comparator() {
			super(TuplePair.class);
		}
		
		public int compare(byte[] b1, int s1, int l1,
				byte[] b2, int s2, int l2) {
			return compareBytes(b1, s1, l1, b2, s2, l2);
		}
		
//		@SuppressWarnings("rawtypes")
//		@Override
      public int compare(TuplePair ip1, TuplePair ip2) {
//              TuplePair ip1 = (TuplePair) w1;
//              TuplePair ip2 = (TuplePair) w2;
              int cmp = ip1.getTupleValue().compareTo(ip2.getTupleValue());
              if (cmp != 0) {
                      return cmp;
              }
              return ip1.getTag()-ip2.getTag(); 
      }

	}
	static {                   // register this comparator
		WritableComparator.define(TuplePair.class, new Comparator());
	}
	*/
	
	@Override
	public int compareTo(TuplePair tuplePair) {
		int cmp=getTupleValue().compareTo(tuplePair.getTupleValue());
		if(cmp!=0){
			return cmp;
		}
		
		return getTag()-tuplePair.getTag();		
	}

}
