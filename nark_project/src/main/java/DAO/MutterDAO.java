package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Mutter;

public class MutterDAO {

	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/nark";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "root";

	// つぶやきを保存
	public boolean add(Mutter mutter) {

		String sql = "INSERT INTO mutter(mutter_num,ac_id,mutter_text,mutter_image,mutter_date,pet_num)"
				+ " VALUES(?,?,?,?,?,?);";
		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
					PreparedStatement pStmt = conn.prepareStatement(sql);) {

				pStmt.setInt(1, mutter.getNum());
				pStmt.setString(2, mutter.getAcId());
				pStmt.setString(3, mutter.getText());
				pStmt.setString(4, mutter.getImage());
				pStmt.setString(5, mutter.getDate());
				pStmt.setInt(6, mutter.getPetNum());

				int result = pStmt.executeUpdate();
				if (result != 1) {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// つぶやきの削除
	public boolean delete(int mutterNum) {
		String sql = "DELETE FROM mutter " + "WHERE mutter_num = ? ;";

		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
					PreparedStatement pStmt = conn.prepareStatement(sql);) {

				pStmt.setInt(1, mutterNum);
				pStmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// ユーザーiDからつぶやきリストを検索
	public ArrayList<Mutter> display(String acId) {
		// 全検索結果を格納するリストを準備
		ArrayList<Mutter> mutterList = new ArrayList<>();
		// 日付順にユーザーのつぶやきを取得
		String sql = "SELECT * FROM mutter " + "WHERE ac_id=?" + " ORDER BY mutter_date DESC;";
		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
					PreparedStatement pStmt = conn.prepareStatement(sql);) {

				pStmt.setString(1, acId);

				ResultSet rs = pStmt.executeQuery();
				mutterList = makeResultData(rs);
				rs.close(); // pStmtが閉じられると自動的に閉じられるため、finally処理不要。

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		return mutterList;
	}

	// 取得したつぶやきをリスト化
	public ArrayList<Mutter> makeResultData(ResultSet rs) throws Exception {
		// 全検索結果を格納するリストを準備
		ArrayList<Mutter> mutterList = new ArrayList<>();
		while (rs.next()) {
			// 1レコード文のデータ読み込み
			Integer num = rs.getInt("mutter_num");
			String acId = rs.getString("ac_id");
			String text = rs.getString("mutter_text");
			String image = rs.getString("mutter_image");
			String date = rs.getString("mutter_date");
			Integer petNum = rs.getInt("pet_num");
			// 1レコード文のデータを格納
			Mutter mutter = new Mutter(num, acId, text, image, date, petNum);
			mutterList.add(mutter);
		}

		return mutterList;
	}

	// 複数ユーザーのつぶやきを取得
	public ArrayList<Mutter> displayAny(String[] acId) {
		// 全検索結果を格納するリストを準備
		ArrayList<Mutter> mutterList = new ArrayList<>();

		// 日付順に複数ユーザーのつぶやきを取得
		String sql = "SELECT * FROM mutter " + "WHERE ac_id=?";
		for (int i = 0; i < (acId.length - 1); i++) {
			sql += "OR ac_id=?";
		}
		sql += " ORDER BY mutter_date DESC;";
		try {
			Class.forName("org.postgresql.Driver");
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
					PreparedStatement pStmt = conn.prepareStatement(sql);) {
				for (int i = 0; i < acId.length; i++) {
					pStmt.setString(i + 1, acId[i]);
				}
				ResultSet rs = pStmt.executeQuery();
				mutterList = makeResultData(rs);
				rs.close(); // pStmtが閉じられると自動的に閉じられるため、finally処理不要。

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		return mutterList;
	}

	//検索：ペットの種類の場合
	

}
