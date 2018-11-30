package modules;

public class ModuleFactory {

	public static Module[] getStandardModuleList() {

		Module[] module_list = new Module[] {
				new ProbOneZero(),
				//new BarGraph(), 
		};

		return (module_list);
	}

}