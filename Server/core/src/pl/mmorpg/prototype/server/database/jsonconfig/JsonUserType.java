package pl.mmorpg.prototype.server.database.jsonconfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.mmorpg.prototype.SpringContext;

public abstract class JsonUserType<T> implements UserType
{

    @Override
    public int[] sqlTypes()
    {
        return new int[] { Types.VARCHAR };
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
            oos.close();
            bos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            return new ObjectInputStream(bais).readObject();
        } catch (ClassNotFoundException | IOException ex)
        {
            throw new HibernateException(ex);
        }
    }

    @Override
    public boolean isMutable()
    {
        return true;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException
    {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException
    {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException
    {
        return this.deepCopy(original);
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException
    {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException
    {
        return Objects.hashCode(x);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException
    {
        final String cellContent = rs.getString(names[0]);
        if (cellContent == null)
            throw new RuntimeException("cell Content is null");

        final ObjectMapper mapper = SpringContext.getBean(ObjectMapper.class);
        try
        {
            return mapper.readValue(cellContent, returnedClass());
        } catch (final IOException ex)
        {
            throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
        }

    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException
    {
        if (value == null)
        {
            st.setNull(index, Types.VARCHAR);
            return;
        }
        try
        {
            final ObjectMapper mapper = new ObjectMapper();
            final StringWriter w = new StringWriter();
            mapper.writeValue(w, value);
            w.flush();
            st.setObject(index, w.toString(), Types.VARCHAR);
        } catch (final Exception ex)
        {
            throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
        }

    }

}
