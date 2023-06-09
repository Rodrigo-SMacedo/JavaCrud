package DAOs;

import java.sql.*;
import java.util.*;

import Classes.*;
import ConnectionFactory.ConnectionFactory;

public class StoreDAO {

    public static void createStore(Store store) {

        String sql = "INSERT INTO store(fantasy_name, CNPJ, contact_id, allotment_address, note, model, social_reason, activity, salesOrServiceProvision) " + "VALUES ('"
                + store.getFantasy_name() + "', '" + store.getCNPJ() + "', " + store.getContact_id() + ", '"
                + store.getAllotment_address() + "', '"+store.getObsText()+"', '"+store.getModel()+"', '"+store.getSocial_reason()+"','"+store.getActivity()+"','"+store.getSalesOrServiceProvision()+"')";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);
            pstm.execute();
            // System.out.println("\n===========\n"+sql+"\n===========\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Store> getStores() {
        String sql = "SELECT * FROM store " + " INNER JOIN contact ON store.contact_id = contact.contact_id"
                + " INNER JOIN allotment ON store.allotment_address = allotment.address";

        List<Store> stores = new ArrayList<Store>();

        Connection conn = null;
        PreparedStatement pstm = null;

        ResultSet rset = null;
        
        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);       
            rset = pstm.executeQuery();
            while (rset.next()) {
                Store store = new Store(rset.getString("fantasy_name"), rset.getString("CNPJ"), rset.getString("social_reason"),
                rset.getString("activity"),rset.getString("model"), rset.getString("note"), rset.getString("salesOrServiceProvision"));
                store.setId(rset.getInt("id"));
                store.setAllotment_address(rset.getString("allotment_address"));
                store.setContact_id(rset.getInt("contact_id"));
                store.setAllotment(new Allotment(rset.getString("address"), rset.getDouble("rent"),
                        rset.getString("area"), rset.getBoolean("available")));

                Contact contact = new Contact(rset.getString("responsible"), rset.getString("email"),
                        rset.getString("phone_1"));
                contact.setPhone_2(rset.getObject("phone_2"));
                contact.setContact_id(rset.getInt("contact_id"));
                store.setContact(contact);

                stores.add(store);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stores;
    }

    public static Object getObject(int id) {
        String sql = "SELECT * " + "FROM store " + "INNER JOIN contact ON store.contact_id = contact.contact_id "
                + "INNER JOIN allotment ON store.allotment_address = allotment.address " + "WHERE store.id = " + id;

        Connection conn = null;
        PreparedStatement pstm = null;

        ResultSet rset = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            if (rset.next()) {
                Store store = new Store(rset.getString("fantasy_name"), rset.getString("CNPJ"), rset.getString("social_reason"),
                rset.getString("activity"),rset.getString("model"), rset.getString("note"), rset.getString("salesOrServiceProvision"));
                store.setId(rset.getInt("id"));
                store.setAllotment_address(rset.getString("allotment_address"));
                store.setContact_id(rset.getInt("contact_id"));
                store.setAllotment(new Allotment(rset.getString("address"), rset.getDouble("rent"),
                        rset.getString("area"), rset.getBoolean("available")));
                Contact contact = new Contact(rset.getString("responsible"), rset.getString("email"),
                        rset.getString("phone_1"));
                contact.setPhone_2(rset.getObject("phone_2"));
                contact.setContact_id(rset.getInt("contact_id"));
                store.setContact(contact);

                return store;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static List<Object> getWithName(String columnName, String whereFind, String equalsTo) {
        String sql = "SELECT " + columnName + " " + "FROM store "
                + "INNER JOIN contact ON store.contact_id = contact.contact_id "
                + "INNER JOIN allotment ON store.allotment_address = allotment.address " + "WHERE store." + whereFind
                + " = '"+equalsTo+"'";
                
        List<Object> returns = new ArrayList<Object>();

        Connection conn = null;
        PreparedStatement pstm = null;

        ResultSet rset = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                returns.add(rset.getObject(columnName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return returns;
    }
    public static void updateStore(Store store, int param) {
        String sql = "UPDATE store SET " + "fantasy_name = '" + store.getFantasy_name() + "', " + "CNPJ = '"
                + store.getCNPJ() + "', social_reason = '"+store.getSocial_reason()+"', note='"+store.getObsText()+"', salesOrServiceProvision= '"+store.getSalesOrServiceProvision()+"', activity='"+store.getActivity()+"', model='"+store.getModel()+"' '"+"' WHERE id = '"+ param + "'";
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getLastStoreId() {
        String sql = "SELECT MAX(id) as id FROM store;";

        int lastStoreId = -1;

        Connection conn = null;
        PreparedStatement pstm = null;

        ResultSet rset = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            lastStoreId = (rset.next()) ? rset.getInt("id") : -1;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lastStoreId;
    }
    public static void deleteStore(int id) {
        System.out.println(id);
        String sql = "DELETE FROM store WHERE contact_id = " + id + "";
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
