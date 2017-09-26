import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CategoryTreeAgent extends ArrayAgent {
	
	private String kbId;
	private List<Map> result;
	
	public CategoryTreeAgent (Object data, String startPath, String kbId) {
		super (data);
		this.kbId = kbId;
		setData (pickCategoriesInPlain (startPath));
	}
	
	private List<Map> pickCategoriesInPlain(String startPath) {
		result = new ArrayList<Map> ();
		List x = jp.getList(startPath+".findAll { d -> d.kbId.equals('"+kbId+"') }");
		Map container = (Map)x.get(0);
		List<Map> categories = (List<Map>)container.get("categories");
		collectRecursive(categories);
		return result;
	}

	private void collectRecursive(List<Map> categories) {
		for (Map c : categories) {
			result.add (c);
			List<Map> children = (List<Map>)c.get("childrenCategories");
			if (children!=null && children.size () > 0)
				collectRecursive(children);
		}
	}
	

}
