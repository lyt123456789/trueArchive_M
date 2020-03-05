package cn.com.trueway.archiveModel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_RSA_SIGN")
public class RsaMes {

	@Id
	@Column(name="ID")
	private String id;
	@Column(name="MESID")
	private String mesId;
	@Column(name="IDSTRUCTURE")
	private String idStructure;
	@Column(name="PRIVATEKEY")
	private String privateKey;
	@Column(name="PUBLICKEY")
	private String publicKey;
	@Column(name="BITLENGTH")
	private String bitLength;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMesId() {
		return mesId;
	}
	public void setMesId(String mesId) {
		this.mesId = mesId;
	}
	public String getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(String idStructure) {
		this.idStructure = idStructure;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getBitLength() {
		return bitLength;
	}
	public void setBitLength(String bitLength) {
		this.bitLength = bitLength;
	}
	public RsaMes() {
		super();
	}
	public RsaMes(String id, String mesId, String idStructure, String privateKey, String publicKey, String bitLength) {
		super();
		this.id = id;
		this.mesId = mesId;
		this.idStructure = idStructure;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.bitLength = bitLength;
	}
	@Override
	public String toString() {
		return "RsaMes [id=" + id + ", mesId=" + mesId + ", idStructure=" + idStructure + ", privateKey=" + privateKey
				+ ", publicKey=" + publicKey + ", bitLength=" + bitLength + "]";
	}
	
}
