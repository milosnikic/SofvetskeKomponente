package util;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class Client {
	private DbxRequestConfig config;
	private DbxClientV2 client;
	private String accessToken;

	public Client(String config, String accessToken) {
		this.accessToken = accessToken;
		this.config = DbxRequestConfig.newBuilder(config).build();
		this.client = new DbxClientV2(this.config, accessToken);
	}

	public DbxRequestConfig getConfig() {
		return config;
	}

	public void setConfig(DbxRequestConfig config) {
		this.config = config;
	}

	public DbxClientV2 getClient() {
		return client;
	}

	public void setClient(DbxClientV2 client) {
		this.client = client;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
