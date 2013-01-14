/*
 * 	book.java 利用豆瓣网API对书籍进行查询
 * 	时间：2013年1月13日14:57:58
 * 	作者：伯广雨
 * 	Eclipse10.0
 */
package com.BookGet;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;

//import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.swing.*;

import net.sf.json.*;

/**
 * 通过豆瓣网API获取书籍信息
 * @author sea7robber
 *
 */
@SuppressWarnings("serial")
public class BookGet extends JFrame implements ActionListener,MouseListener {
	private JTextField m_input;
	private JButton m_search;
	private JTextArea m_result;
	
	public static void main(String[] args){
		BookGet bookGet = new BookGet();
	}
	
	BookGet(){
		JLabel title = new JLabel("show your result here");
		m_result = new JTextArea();
		m_result.setEditable(false);
		JScrollPane resultScroll = new JScrollPane(m_result);
		m_input=new JTextField("search here");
		m_input.addMouseListener(this);
		m_input.setBounds(20, 20, 240, 30);
		m_search = new JButton("Book Get");
		m_search.addActionListener(this);
		m_search.setBounds(350, 20, 40, 30);
		JPanel search = new JPanel();
		search.add(m_input);
		search.add(m_search);

		this.add(title, "North");
		this.add(resultScroll, "Center");
		this.add(search, "South");
		
		int width = 500, height = 300;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setSize(width, height);
	    this.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Book Get");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		
		 if(action.getSource()==m_search){
			 if(m_input.getText().toString().equals("")) return ;
			 String result = m_input.getText().trim().toString();
			 String url="https://api.douban.com/v2/book/search?q="+result+"&start=0&count=10";
			 m_input.setText("");
			 try{
				 DefaultHttpClient client = new DefaultHttpClient();
                 HttpGet get = new HttpGet(url);   
                 HttpResponse response = client.execute(get);
                 result=EntityUtils.toString(response.getEntity()); 
			 }catch(Exception e){	  
				 e.printStackTrace();				 
			 } 
			 
			 if(result.length() == 0){
				 m_result.setText("╮(╯▽╰)╭ NOT FOUND ╮(╯▽╰)╭");
			 }
			 else {
				 try{						
						JSONObject jar=JSONObject.fromObject(result);
						JSONArray array=jar.getJSONArray("books"); 
						int resultSize=array.size();    
						
						for(int i = 0;i < resultSize; i++){
							JSONObject obj =  array.getJSONObject(i);
							
							m_result.append("--------------  result " + (i + 1) + "  --------------" + "\n");
			
							m_result.append("name: " + obj.getString("title") + "\n");
							
							m_result.append("author: " + obj.getString("author") + "\n");
							
							m_result.append("publisher: " + obj.getString("publisher") + "\n");
							
							m_result.append("datetime: " + obj.getString("pubdate") + "\n");
							
							m_result.append("price: " + obj.getString("price") + "\n");	
						}	
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			 }		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(m_input.getText().toString().equals("search here")){
			m_input.setText("");
		}		 
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(m_input.getText().toString().equals("")){
			m_input.setText("search here");
		}
			
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
