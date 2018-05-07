package top.codermartin.webexplorer.Dao;

public class ImageDao extends FileDao {
	public ImageDao(String name, String path, String format) {
		super(name, path);
		this.format = format;
	}

	private String format;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
