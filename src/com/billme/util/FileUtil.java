package com.billme.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.futurePayment.http.MyHttpClient;

public class FileUtil {
	private MyHttpClient client = null;
	private String ROOTPATH;
	private String APP_NAME = "BillMe";
	private String USER_NAME = null;

	public FileUtil(String name) {
		USER_NAME = name;
		ROOTPATH = Environment.getExternalStorageDirectory() + File.separator
				+ APP_NAME + File.separator + USER_NAME + File.separator;
		client = new MyHttpClient(name);
		createSDRootDirectory();
		// 创建一系列文件夹
		createSDDirectory("cache");
	}

	/**
	 * 测试sd卡是否可用
	 * 
	 * @return 结果
	 */
	public boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	/**
	 * 得到文件大小
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return 文件大小
	 */
	public int getFileSize(String path) {
		FileInputStream fis = null;
		File file = new File(ROOTPATH + path);
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				return fis.available();
			} else
				return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 得到文件大小
	 * 
	 * @param dir
	 *            文件目录路径
	 * @param name
	 *            文件名
	 * @return 文件大小
	 */
	public int getFileSize(String dir, String name) {
		FileInputStream fis = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				return fis.available();
			} else
				return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return 创建的文件，若出错，返回null
	 * @throws IOException
	 */
	public File createSDFile(String path) {
		File file = null;
		try {
			file = new File(path);
			file.createNewFile();
			if (!file.isFile()) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 创建文件
	 * 
	 * @param dir
	 *            目录路径
	 * @param name
	 *            文件名
	 * @return 创建的文件，若出错，返回null
	 * @throws IOException
	 */
	public File createSDFile(String dir, String name) {
		File file = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		try {
			file = new File(ROOTPATH + dir, n);
			file.createNewFile();
			if (!file.isFile()) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 创建根目录
	 * 
	 * @param path
	 *            目录路径
	 * @return 创建的目录,若出错，返回null
	 */
	public File createSDRootDirectory() {
		File dir = new File(ROOTPATH);
		dir.mkdirs();
		if (!dir.isDirectory()) {
			return null;
		}
		return dir;
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 *            目录路径
	 * @return 创建的目录,若出错，返回null
	 */
	public File createSDDirectory(String path) {
		File dir = new File(ROOTPATH + path);
		dir.mkdirs();
		if (!dir.isDirectory()) {
			return null;
		}
		return dir;
	}

	/**
	 * 检测文件是否存在
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return 是否存在文件
	 */
	public boolean isFileExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 检测文件是否存在
	 * 
	 * @param dir
	 *            目录路径
	 * @param name
	 *            文件名
	 * @return 是否存在文件
	 */
	public boolean isFileExists(String dir, String name) {
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		return file.exists();
	}

	/**
	 * 检测文件与下载文件是否为同一文件，通过比较大小确定
	 * 
	 * @param path
	 *            文件绝对路径
	 * @param size
	 *            下载文件大小
	 * @return 是否为同一文件
	 */
	public boolean isSameFile(String path, int size) {
		if (getFileSize(path) == size) {
			return true;
		}
		return false;
	}

	/**
	 * 检测文件与下载文件是否为同一文件，通过比较大小确定
	 * 
	 * @param dir
	 *            目录路径
	 * @param name
	 *            文件名
	 * @param size
	 *            下载文件大小
	 * @return 是否为同一文件
	 */
	public boolean isSameFile(String dir, String name, int size) {
		String n = name.replaceAll("/", "").replaceAll(":", "");
		if (getFileSize(dir, n) == size) {
			return true;
		}
		return false;
	}

	/**
	 * 通过inputStream写入数据到sd卡
	 * 
	 * @param dir
	 *            文件目录路径
	 * @param fileName
	 *            文件名
	 * @param input
	 *            输入流
	 * @param cover
	 *            若文件存在，是否覆盖
	 * @return 生成的文件
	 */
	public File writeToSDFromInputStream(String dir, String fileName,
			InputStream input, boolean cover) {		
		FileOutputStream output = null;
		String fn = fileName.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, fileName);
		try {
			createSDDirectory(dir);
			if (isFileExists(dir, fn) && cover == false) {
				output = new FileOutputStream(file, true);
			} else {
				file = createSDFile(dir, fn);
				output = new FileOutputStream(file);
				
			}
			byte buffer[] = new byte[1024];
			while (input.read(buffer) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 从SD卡读取文件
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return
	 */
	public String readFromSD(String path) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		File file = new File(ROOTPATH + path);
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 从SD卡读取文件
	 * 
	 * @param dir
	 *            文件目录路径
	 * @param name
	 *            文件名
	 * @return
	 */
	public String readFromSD(String dir, String name) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 读取sd卡里的图片
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return 图片drawable
	 */
	public Drawable readImageFromSD(String path) {
		Drawable image = null;
		FileInputStream inputStream = null;
		File file = new File(ROOTPATH + path);
		if (isSDCardAvailable()) {
			try {
				inputStream = new FileInputStream(file);
				image = Drawable.createFromStream(inputStream, file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return image;
	}

	/**
	 * 读取sd卡里的图片
	 * 
	 * @param dir
	 *            文件目录路径
	 * @param name
	 *            文件名
	 * @return 图片drawable
	 */
	public Drawable readImageFromSD(String dir, String name) {
		Drawable image = null;
		FileInputStream inputStream = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		if (isSDCardAvailable()) {
			try {
				inputStream = new FileInputStream(file);
				image = Drawable.createFromStream(inputStream, file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return image;
	}

	/**
	 * 
	 * @param urlStr
	 *            文件url地址
	 * @param dir
	 *            存储到sd目录路径
	 * @param fileName
	 *            存储的文件名
	 * @param cover
	 *            若文件已存在，是否覆盖
	 * @return 下载是否成功
	 */
	public boolean downloadFile(String urlStr, String dir, String fileName,
			boolean cover) {
		InputStream inputStream = null;
		String n = fileName.replaceAll("/", "").replaceAll(":", "");
		try {
			inputStream = client.getInputStreamFromUrl(urlStr);
			File file = writeToSDFromInputStream(dir, n, inputStream,
					cover);
			if (file == null) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 把实体转化成存储地址
	 * 
	 * @param urlStr
	 * @return
	 */
	public String modelToAddress(Object model) {
		String name = model.getClass().getName();
		return ROOTPATH + name;
	}

}
