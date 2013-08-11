package crm.df.crm.tenant;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SecondaryTable;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@SecondaryTable(name = "TENANT_ADDRESS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ADDRESS_ID"))
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;

    @Column(length = 128, nullable = false)
    private String name;

    @Column
    private long ownerId;

    @Lob
    @Column(nullable = false)
    private String description;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "country", column = @Column(table = "TENANT_ADDRESS")),
	    @AttributeOverride(name = "province", column = @Column(table = "TENANT_ADDRESS")),
	    @AttributeOverride(name = "city", column = @Column(table = "TENANT_ADDRESS")),
	    @AttributeOverride(name = "county", column = @Column(table = "TENANT_ADDRESS")),
	    @AttributeOverride(name = "town", column = @Column(table = "TENANT_ADDRESS")),
	    @AttributeOverride(name = "address", column = @Column(table = "TENANT_ADDRESS")) })
    private String address;

}
