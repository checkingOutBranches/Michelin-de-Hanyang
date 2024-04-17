using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LivestockProduct : MonoBehaviour
{
    [SerializeField]
    private SOItem soItem;
    
    public void Get() {
        Destroy(gameObject);
    }
}
