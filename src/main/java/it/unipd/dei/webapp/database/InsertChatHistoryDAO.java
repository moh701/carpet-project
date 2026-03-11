package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.ChatHistory;
import it.unipd.dei.webapp.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertChatHistoryDAO {
    private static final String STATEMENT_CHAT_INSERT = "INSERT INTO carpet.chat_history(producer_id, customer_id, datetime, message_content) VALUES(?, ?, ?, ?)";
    private final Connection con;
    private final ChatHistory chat_history;

    public InsertChatHistoryDAO(Connection con, ChatHistory chat_history) {
        this.con = con;
        this.chat_history = chat_history;
    }

    public int insertChatHistory() throws SQLException {
        PreparedStatement stmnt = null;
        try {
            stmnt = con.prepareStatement(STATEMENT_CHAT_INSERT);
            stmnt.setInt(1, chat_history.getProducer_id());
            stmnt.setInt(2, chat_history.getCustomer_id());
            stmnt.setTimestamp(3, chat_history.getDatetime());
            stmnt.setString(4, chat_history.getMessage_content());
            int rs = stmnt.executeUpdate();
            if(rs == 1){
                return 1;
            } else{
                return 0;
            }
        } catch(Exception e)
        {
            return -4;
        }
        finally {
            if (stmnt != null) {
                stmnt.close();
            }
            con.close();
        }
    }
}
