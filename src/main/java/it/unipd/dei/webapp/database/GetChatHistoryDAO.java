package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.ChatHistory;
import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.resource.Producer;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetChatHistoryDAO {
    private static final String STATEMENT_CHAT_GET = "SELECT chat_id, producer_id, customer_id, datetime, message_content FROM carpet.chat_history WHERE " +
            "customer_id = (SELECT customer_id FROM carpet.customer WHERE email_address = ?) and producer_id = (SELECT producer_id FROM carpet.producer " +
            "WHERE email_address = ?)";
    private final Connection con;
    private final Customer customer;
    private final Producer producer;

    public GetChatHistoryDAO(Connection con, Customer customer, Producer producer) {
        this.con = con;
        this.customer = customer;
        this.producer = producer;
    }

    public ChatHistory getChatHistory() throws SQLException{
        PreparedStatement stmnt = null;
        ResultSet result = null;
        ChatHistory chat_history = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CHAT_GET);
            stmnt.setString(1, customer.getEmail_address());
            stmnt.setString(2, producer.getEmail_address());
            result = stmnt.executeQuery();
            if (result.next()) {
                chat_history = new ChatHistory(result.getInt(1), result.getInt(2), result.getInt(3), result.getTimestamp(4), result.getString(5));
            }
            return chat_history;
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
