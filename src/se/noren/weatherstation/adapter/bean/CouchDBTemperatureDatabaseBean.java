package se.noren.weatherstation.adapter.bean;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CouchDBTemperatureDatabaseBean {

	private int total_rows; 
	private int offset;
	private List<DBRow> rows;
	
	public int getTotal_rows() {
		return total_rows;
	}


	public void setTotal_rows(int total_rows) {
		this.total_rows = total_rows;
	}


	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public List<DBRow> getRows() {
		return rows;
	}


	public void setRows(List<DBRow> rows) {
		this.rows = rows;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	
	public static class DBRow {

		private String id;
		private String key;
		private DBValue value;
		private DBDoc doc;
		
		public DBRow() {
			
		}
		
		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getKey() {
			return key;
		}


		public void setKey(String key) {
			this.key = key;
		}


		public DBDoc getDoc() {
			return doc;
		}


		public void setDoc(DBDoc doc) {
			this.doc = doc;
		}



		public DBValue getValue() {
			return value;
		}

		public void setValue(DBValue value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

		public static class DBValue {
			private String rev;

			public DBValue() {}
			
			public String getRev() {
				return rev;
			}

			public void setRev(String rev) {
				this.rev = rev;
			}
			
			
		}
		
		public static class DBDoc {
			private String _id;
			private String _rev;
			private Double temperature;
			private Long rawDate;
			private String key;

			public DBDoc() {
				
			}
			
			public String get_id() {
				return _id;
			}



			public void set_id(String _id) {
				this._id = _id;
			}



			public String get_rev() {
				return _rev;
			}



			public void set_rev(String _rev) {
				this._rev = _rev;
			}



			public Double getTemperature() {
				return temperature;
			}



			public void setTemperature(Double temperature) {
				this.temperature = temperature;
			}



			public Long getRawDate() {
				return rawDate;
			}



			public void setRawDate(Long rawDate) {
				this.rawDate = rawDate;
			}



			public String getKey() {
				return key;
			}



			public void setKey(String key) {
				this.key = key;
			}



			@Override
			public String toString() {
				return ToStringBuilder.reflectionToString(this);
			}
		}
	}

}
