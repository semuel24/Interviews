package machine.loader;

import java.util.List;

public class CallResult
{
    private List<UserWrapper> results;
    private String nationality;
    private String seed;
    private String version;

	public List<UserWrapper> getResults() {
		return results;
	}

	public void setResults(List<UserWrapper> results) {
		this.results = results;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
