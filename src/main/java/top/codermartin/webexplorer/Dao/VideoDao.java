package top.codermartin.webexplorer.Dao;

public class VideoDao extends FileDao {
	public VideoDao(String name, String path, String format) {
		super(name, path);
		this.format = format;
	}
	private String format = "mp4";
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
