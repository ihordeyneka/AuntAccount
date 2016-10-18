package dido.auntaccount.utils;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.joda.time.DateTime;
import java.sql.Timestamp;

public class JodaDateTimeConverter implements Converter {

    private static final long serialVersionUID = 1L;

    public Object convertDataValueToObjectValue(Object dataValue, Session session) {
        return dataValue == null ? null : new DateTime(dataValue);
    }

    public Object convertObjectValueToDataValue(Object objectValue, Session session) {
        return objectValue == null ? null : new Timestamp(((DateTime) objectValue).getMillis());
    }

    public void initialize(DatabaseMapping mapping, Session session) {
        // this method can be empty if you are not creating DB from Entity classes
        mapping.getField().setType(java.sql.Timestamp.class);
    }

    public boolean isMutable() {
        return false;
    }

}