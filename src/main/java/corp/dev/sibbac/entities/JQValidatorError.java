package corp.dev.sibbac.entities;

import java.io.Serializable;

/**
 *	Encapsula datos de errores por validacion jQuery. 
 */
public class JQValidatorError implements Serializable {
	
	private static final long serialVersionUID = -1107285158899675485L;
	
	private String id;
	private String error;

	public JQValidatorError(String id, String error) {
		this.id = id;
		this.error = error;
	}

	/**
	 * 	getId.
	 * 	@return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 	setId.
	 * 	@param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 	getError.
	 * 	@return error
	 */
	public String getError() {
		return this.error;
	}

	/**
	 * 	setError.
	 * 	@param error 
	 */
	public void setError(String error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int a = 0;
		if (this.id == null) {
			a = 0;
		} else {
			a = this.id.hashCode();
		}
		result = prime * result + a;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}	
		if (!(obj instanceof JQValidatorError)) {
			return false;
		}	
		JQValidatorError other = (JQValidatorError) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}	
		} else if (!this.id.equals(other.id)) {
			return false;
		}	
		return true;
	}
	
}
