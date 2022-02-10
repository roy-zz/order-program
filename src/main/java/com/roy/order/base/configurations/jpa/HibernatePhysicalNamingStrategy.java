package com.roy.order.base.configurations.jpa;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

@Slf4j
public class HibernatePhysicalNamingStrategy implements PhysicalNamingStrategy
{

    @Override
    public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv)
    {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv)
    {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv)
    {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv)
    {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv)
    {
        return convertToSnakeCase(identifier);
    }

    private Identifier convertToSnakeCase(final Identifier identifier)
    {
        if (identifier == null)
        {
            return null;
        }
        if(identifier.getText().matches("___[a-zA-Z]+___"))
        {
            String newName = identifier.getText().replaceAll("___([a-zA-Z]+)___", "$1");
            return Identifier.toIdentifier(newName);
        }
        String newName = identifier.getText().replaceAll("([a-z])([A-Z]+)", "$1_$2").replaceAll(
                "([A-Z])([A-Z])([a-z]+)", "$1_$2$3").toLowerCase();
        return Identifier.toIdentifier(newName);
    }

}
