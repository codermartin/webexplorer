package top.codermartin.webexplorer;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.codermartin.webexplorer.Dao.DirDao;
import top.codermartin.webexplorer.Dao.FileDao;
import top.codermartin.webexplorer.Dao.ImageDao;
import top.codermartin.webexplorer.Dao.VideoDao;

@Controller
// @RestController
public class PageController {

	String[] imageTypes = new String[] { "bpm", "png", "jpg", "jpeg" };
	String[] videoTypes = new String[] { "avi", "mp4", "mkv", "rm", "rmvb", "wmv" };
	@Value("${project.path_prefix}")
	String path_prefix;
	@Value("${project.static_prefix}")
	String static_prefix;

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	private boolean isValid(String filename, String... allowTypes) {
		if (null == filename || "".equals(filename)) {
			return false;
		}
		for (String type : allowTypes) {
			if (filename.endsWith(type)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/explore";
	}

	@RequestMapping(value = "/explore/**")
	public String mainpage(HttpServletRequest request, Model model) {
		String uri = request.getRequestURI();

		String pure_uri = uri.substring("/explore".length());
		File file = new File(path_prefix + "\\" + static_prefix + "\\" + pure_uri);

		if (file.exists() && file.isDirectory()) {
			ArrayList<ImageDao> images = new ArrayList<>();
			ArrayList<VideoDao> videos = new ArrayList<>();
			ArrayList<FileDao> files = new ArrayList<>();
			ArrayList<DirDao> dirs = new ArrayList<>();
			File[] cfiles = file.listFiles();
			if (cfiles != null) {
				for (File cfile : cfiles) {
					String name = cfile.getName();
					String path = request.getRequestURL() + "/" + cfile.getName();
					if (cfile.isFile()) {
						if (isValid(cfile.getName(), imageTypes)) {
							images.add(new ImageDao(name, path, getExtensionName(cfile.getPath())));
						} else if (isValid(cfile.getName(), videoTypes)) {
							videos.add(new VideoDao(name, path, getExtensionName(cfile.getPath())));
						} else {
							files.add(new FileDao(name, path));
						}
					}else if(file.isDirectory()){
						dirs.add(new DirDao(name, path+"/"));
					}
				}
			}
			System.out.println(videos);
			System.out.println(files);
			System.out.println(images);
			model.addAttribute("title", "web explorer");
			model.addAttribute("videos", videos);
			model.addAttribute("images", images);
			model.addAttribute("files", files);
			model.addAttribute("dirs", dirs);
			return "index";
		} else {
			return "redirect:/"+ static_prefix + pure_uri;
		}
	}
}