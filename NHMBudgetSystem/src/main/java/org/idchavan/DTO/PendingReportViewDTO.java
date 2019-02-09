package org.idchavan.DTO;

import java.math.BigDecimal;
import java.math.MathContext;

public class PendingReportViewDTO {
	
	/**This field hold the program name of Sanction Order Details. */
	private String programName;
	
	/**This field hold the <b>Sanction Order <b/> info. */
	private CommanProperty sanctionOrder = new CommanProperty();
	
	/**This field hold the <b>State Or Central Share<b/> info depend upon selected type. */
	private CommanProperty stateShare = new CommanProperty();
	
	/**This field hold the <b>received(Bank)<b/> info. */
	private CommanProperty stateShareRecevied = new CommanProperty();
	
	/**This field hold the <b>received(Bank)<b/> info. */
	private CommanProperty centralShareRecevied = new CommanProperty();
	
	/**This field hold the <b>Yet to be not received(Pending)<b/> info. */
	private CommanProperty pending = new CommanProperty();
	
	
	public PendingReportViewDTO() {
		
	 }
	
	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	/**
	 * @return the sanctionOrder
	 */
	public CommanProperty getSanctionOrder() {
		return sanctionOrder;
	}

	/**
	 * @return the stateShare
	 */
	public CommanProperty getStateShare() {
		return stateShare;
	}

	/**
	 * @return the stateSharesecevied
	 */
	public CommanProperty getStateShareRecevied() {
		return stateShareRecevied;
	}

	/**
	 * @return the centralSharesecevied
	 */
	public CommanProperty getCentralShareRecevied() {
		return centralShareRecevied;
	}

	/**
	 * @return the pending
	 */
	public CommanProperty getPending() {
		return pending;
	}

	public class CommanProperty {
		private String name;
		private BigDecimal general = new BigDecimal("0.00");
		private BigDecimal scp = new BigDecimal("0.00");
		private BigDecimal tsp = new BigDecimal("0.00");
		private BigDecimal total = new BigDecimal("0.00");
		
		public CommanProperty() {
		}		
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the general
		 */
		public BigDecimal getGeneral() {
			return general;
		}

		/**
		 * @param general the general to set
		 */
		public void setGeneral(BigDecimal general) {
			this.general = this.general.add(general);
		}

		/**
		 * @return the scp
		 */
		public BigDecimal getScp() {
			return scp;
		}

		/**
		 * @param scp the scp to set
		 */
		public void setScp(BigDecimal scp) {
			this.scp = this.scp.add( scp );
		}

		/**
		 * @return the tsp
		 */
		public BigDecimal getTsp() {
			return tsp;
		}

		/**
		 * @param tsp the tsp to set
		 */
		public void setTsp(BigDecimal tsp) {
			this.tsp = this.tsp.add(tsp);
		}

		/**
		 * @return the total
		 */
		public BigDecimal getTotal() {
			return total;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(BigDecimal total) {
			this.total = total;
		}
		
		/**
		 * This method calculating the total amount of three categories<br>
		 * and setting to total var.<br>
		 * <code>total=general+scp+tsp<code>
		 */
		public void calculateTotal() {
			setTotal( general.add(scp).add(tsp) );
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CommanProperty [general=" + general + ", scp=" + scp + ", tsp=" + tsp + ", total=" + total + "]";
		}
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PendingReportViewDTO [programName=" + programName + ", sanctionOrder=" + sanctionOrder + ", stateShare="
				+ stateShare + ", stateShareRecevied=" + stateShareRecevied + ", centralShareRecevied="
				+ centralShareRecevied + ", pending=" + pending + "]";
	}

	
}
