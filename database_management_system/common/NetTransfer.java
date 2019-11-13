package common;

import java.io.Serializable;

public class NetTransfer implements Serializable{
	private static final long serialVersionUID = 1L;
	public String action;  // 定义操作类型
	public int id;  // 代表用户或者文件的id
	public String paras;  // 代表用户传过来的参数
	public Object obj;  // 不方便用字符串表示的数据用obj表示

	// 服务器返回信息
	public boolean ifRun;  // 服务器是否正确执行
	public String strErr;  // 如果执行错误, 返回错误信息
	public String strRes;  // 执行正确后的返回信息
	public Object objRes;  // 不方便用字符串表示的, 用obj表示
}
