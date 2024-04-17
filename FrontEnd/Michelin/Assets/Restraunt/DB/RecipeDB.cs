using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[ExcelAsset]
public class RecipeDB : ScriptableObject
{
	public List<RecipeDBEntity> Recipe; // Replace 'EntityType' to an actual type that is serializable.
}
