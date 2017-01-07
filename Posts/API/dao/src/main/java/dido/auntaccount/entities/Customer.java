package dido.auntaccount.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("0")
public class Customer extends User {

}
