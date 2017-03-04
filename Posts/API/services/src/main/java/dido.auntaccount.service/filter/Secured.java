package dido.auntaccount.service.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Target({TYPE, METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@NameBinding
public @interface Secured {
}
